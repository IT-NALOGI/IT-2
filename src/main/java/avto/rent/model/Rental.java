package avto.rent.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@MongoEntity(database = "ITA-service2", collection = "rental")
@Builder
public class Rental extends PanacheMongoEntity {
    public String userId;
    public String avtoId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String status;

    public Rental(String rentalId, String user123, String avto456, LocalDate parse, LocalDate parse1, String active) {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ObjectId getId() {
        if (id == null) {
            id = new ObjectId();
        }
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAvtoId() {
        return avtoId;
    }

    public void setAvtoId(String avtoId) {
        this.avtoId = avtoId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}