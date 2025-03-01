package main.model;

public class WheatField extends Establishment {
    private final int COIN_TO_PAY = 1;
    private final int COIN_TO_GAIN = 1;

    public WheatField() {
        super("小麥田",  1, CardType.CROP, 6, 1, Industry.BLUE);
    }

    @Override
    public void takeEffect(Game game,Player player) {
        // 任何人骰出這個數字時，你都可以從銀行獲得1元
        if (isDicePointToTakeEffect(game.getCurrentDicePoint())) {
            game.getBank().payCoin(COIN_TO_PAY);
            player.gainCoin(COIN_TO_GAIN);
        }
    }
}
