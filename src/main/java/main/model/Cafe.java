package main.model;

import java.util.ArrayList;
import java.util.List;

public class Cafe extends Establishment {
    private final int COIN_TO_PAY = 1;
    private final int COIN_TO_GAIN = 1;

    public Cafe() {
        super("咖啡館", null, 2, CardType.RESTAURANT, 6, 3, Industry.RED);
    }

    @Override
    public void takeEffect(Game game) {
        // 任何人骰出這個數字時，擁有此手牌的玩家能從骰骰子的人獲得1元，並且骰骰的人扣1元
        if (isDicePointToTakeEffect(game.getCurrentDicePoint()) && playerHasEnoughCoin(game)) {

            payCoin(game.getTurnPlayer(), getNeedToPayTimes(game));

            game.getPlayers().stream()
                    .filter(player -> player.getOwnedEstablishment().contains(this))
                    .forEach(this::gainCoin);
        }
    }

    private int getNeedToPayTimes(Game game) {
        return (int) game.getPlayers().stream()
                .filter(player -> player.getOwnedEstablishment().contains(this))
                .count();
    }

    private boolean playerHasEnoughCoin(Game game) {
        return game.getTurnPlayer().getTotalCoin() - getNeedToPayTimes(game) >= 0;
    }

    void payCoin(Player player, int count) {
        player.payCoin(COIN_TO_PAY * count);
    }

    void gainCoin(Player player) {
        player.gainCoin(COIN_TO_GAIN * COIN_TO_GAIN);
    }
}
