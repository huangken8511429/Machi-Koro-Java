package main.model.usecase;

import main.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistributeResourcesTest {
    private Player playerA;
    private Player playerB;
    private Establishment wheatField;
    private Establishment cafe;
    private Game game;

    @BeforeEach
    void setUP() {
        playerA = new Player("A");
        playerB = new Player("B");
        wheatField = new WheatField();
        cafe = new Cafe();
        game = new Game(new Bank(100), List.of(playerA, playerB), List.of(new Dice()), new Marketplace());
    }

    @Test
    @DisplayName(
            "given 有銀行(100 coin)、A 玩家(3 coin)，A 玩家手牌裡有小麥田 " +
                    "when A 玩家擲骰子是1時，系統分配資源後 " +
                    "then A 玩家從銀行獲得1元(銀行 coin = 99, A 玩家 coin = 1)")
    void testWheatField() {
        // given
        playerA.addCardToHandCard(wheatField);

        // when
        game.setTurnPlayer(playerA);
        settingDicePointAndTakeEffect(1, game);

        // then
        assertEquals(99, game.getBank().getTotalCoin());
        assertEquals(4, playerA.getTotalCoin());
    }

    @Test
    @DisplayName(
            "given 有銀行(100 coin)、A 玩家(3 coin)及 B 玩家(3 coin)，B 玩家手牌裡有咖啡館 " +
                    "when A 玩家擲骰子是3時，系統分配資源後 " +
                    "then B 玩家從玩家A獲得1元(B 玩家 coin = 4, A 玩家 coin = 2)")
    void playerB_has_OneCafe() {
        //given
        playerB.addCardToHandCard(cafe);

        // when
        game.setTurnPlayer(playerA);
        settingDicePointAndTakeEffect(3, game);

        // then
        assertEquals(100, game.getBank().getTotalCoin());
        assertEquals(2, playerA.getTotalCoin());
        assertEquals(4, playerB.getTotalCoin());
    }

    @Test
    @DisplayName(
            "given 有銀行(100 coin)、A 玩家(3 coin)及 B 玩家(3 coin)，B 玩家手牌裡有三張咖啡館 " +
                    "when A 玩家擲骰子是3時，系統分配資源後 " +
                    "then B 玩家從玩家A獲得3元(B玩家 coin = 6, A 玩家 coin = 0)")
    void playerB_has_threeCafe() {
        // given
        setPlayer_has_threeCafes(playerB);

        // when
        game.setTurnPlayer(playerA);
        settingDicePointAndTakeEffect(3, game);

        // then
        assertEquals(100, game.getBank().getTotalCoin());
        assertEquals(0, playerA.getTotalCoin());
        assertEquals(6, playerB.getTotalCoin());
    }

    @Test
    @DisplayName(
            "given 有銀行(100 coin)、A 玩家(2 coin)及 B 玩家(3 coin)，B 玩家手牌裡有三張咖啡館 " +
                    "when A 玩家擲骰子是3時，系統分配資源後 " +
                    "then B 玩家從玩家A獲得2元(B玩家 coin = 5, A 玩家 coin = 0)")
    void playerB_has_threeCafe_but_player_money_isNot_enough() {
        // given
        playerA.payCoin(1);
        setPlayer_has_threeCafes(playerB);

        // when
        game.setTurnPlayer(playerA);
        settingDicePointAndTakeEffect(3, game);

        // then
        assertEquals(100, game.getBank().getTotalCoin());
        assertEquals(0, playerA.getTotalCoin());
        assertEquals(5, playerB.getTotalCoin());
    }

    private static void settingDicePointAndTakeEffect(int point, Game game) {
        game.setCurrentDicePoint(point);
        game.distributeResources(game.getCurrentDicePoint());
    }

    private void setPlayer_has_threeCafes(Player player) {
        player.addCardToHandCard(cafe);
        player.addCardToHandCard(cafe);
        player.addCardToHandCard(cafe);
    }
}
