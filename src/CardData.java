import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import java.util.Date;

public class CardData {
    private Date cardSwipeDate;
    private String cardId;

    /**
     * Create an CardData object
     * @param cardSwipeDate date/time of card swipe
     * @param cardId ID read from RFID card
     */
    public CardData(Date cardSwipeDate, String cardId) {
        this.cardSwipeDate = cardSwipeDate;
        this.cardId = cardId;
    }

    public Date getCardSwipeDate() {
        return cardSwipeDate;
    }

    public void setCardSwipeDate(Date cardSwipeDate) {
        this.cardSwipeDate = cardSwipeDate;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public static DBObject createDBObject(CardData data) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
        docBuilder.append("cardSwipeDate", data.getCardSwipeDate());
        docBuilder.append("cardId", data.getCardId());
        return docBuilder.get();
    }
}
