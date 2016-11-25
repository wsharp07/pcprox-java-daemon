import java.util.Date;

public class CardData {
    private Date cardSwipeDate;
    private int cardId;

    /**
     * Create an AttendeeData object
     * @param cardSwipeDate date/time of card swipe
     * @param cardId ID read from RFID card
     */
    public CardData(Date cardSwipeDate, int cardId) {
        this.cardSwipeDate = cardSwipeDate;
        this.cardId = cardId;
    }

    public Date getCardSwipeDate() {
        return cardSwipeDate;
    }

    public void setCardSwipeDate(Date cardSwipeDate) {
        this.cardSwipeDate = cardSwipeDate;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
