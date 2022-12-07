package main.model;

import java.util.ArrayList;
import java.util.List;

public class Cafe extends Establishment {
    private final int COIN_TO_PAY = 1;
    private final int COIN_TO_GAIN = 1;

    public Cafe() {
        super("咖啡館", 2, CardType.RESTAURANT, 6, 3, Industry.RED);
    }

    @Override
    public void takeEffect(Game game) {
        // 任何人骰出這個數字時，擁有此手牌的玩家能從骰骰子的人獲得1元，並且骰骰的人扣1元
        if (isDicePointToTakeEffect(game.getCurrentDicePoint()) && playerHasEnoughCoin(game)) {


            for (int i = 0; i < game.getPlayers().size(); i++) {
                for (int j = 0; j < game.getPlayers().get(i).getOwnedEstablishment().size(); j++) {
                    if (game.getPlayers().get(i).getOwnedEstablishment().get(j).equals(this) && game.getTurnPlayer().getTotalCoin() > 0) {
                        this.gainCoin(game.getPlayers().get(i));
                        this.payCoin(game.getTurnPlayer());
                    }
                }
            }
//          game.getPlayers().stream()
//    .forEach(player -> {
//        player.getOwnedEstablishment().stream()
//            .filter(establishment -> establishment.equals(this))
//            .forEach(establishment -> {
//                this.gainCoin(player);
//                this.payCoin(game.getTurnPlayer());
//            });
//    });
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

    void payCoin(Player player) {
        player.payCoin(COIN_TO_PAY);
    }

    void gainCoin(Player player) {
        player.gainCoin(COIN_TO_GAIN);
    }
}
