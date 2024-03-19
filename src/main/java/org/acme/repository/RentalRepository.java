package org.acme.repository;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Rental;

@ApplicationScoped
public class RentalRepository implements PanacheMongoRepository<Rental> {
}
