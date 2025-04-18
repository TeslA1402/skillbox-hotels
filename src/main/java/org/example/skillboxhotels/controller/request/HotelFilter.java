package org.example.skillboxhotels.controller.request;

import org.example.skillboxhotels.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public record HotelFilter(Long id, String name, String title, String city, String address,
                          Integer lteDistanceFromCenter,
                          Double gteRating, Integer gteNumberOfRatings) {

    public Specification<Hotel> getSpecification() {
        return Specification.where(byId())
                .and(byName())
                .and(byTitle())
                .and(byCity())
                .and(byAddress())
                .and(byDistanceFromCenter())
                .and(byRating())
                .and(byNumberOfRatings());
    }

    private Specification<Hotel> byId() {
        return id == null ? null : (root, query, cb) -> cb.equal(root.get(Hotel.Fields.id.name()), id);
    }

    private Specification<Hotel> byName() {
        return name == null ? null : (root, query, cb) -> cb.like(cb.upper(root.get(Hotel.Fields.name.name())), "%" + name.toUpperCase() + "%");
    }

    private Specification<Hotel> byTitle() {
        return title == null ? null : (root, query, cb) -> cb.like(cb.upper(root.get(Hotel.Fields.title.name())), "%" + title.toUpperCase() + "%");
    }

    private Specification<Hotel> byCity() {
        return city == null ? null : (root, query, cb) -> cb.like(cb.upper(root.get(Hotel.Fields.city.name())), "%" + city.toUpperCase() + "%");
    }

    private Specification<Hotel> byAddress() {
        return address == null ? null : (root, query, cb) -> cb.like(cb.upper(root.get(Hotel.Fields.address.name())), "%" + address.toUpperCase() + "%");
    }

    private Specification<Hotel> byDistanceFromCenter() {
        return lteDistanceFromCenter == null ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Hotel.Fields.distanceFromCenter.name()), lteDistanceFromCenter);
    }

    private Specification<Hotel> byRating() {
        return gteRating == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Hotel.Fields.rating.name()), gteRating);
    }

    private Specification<Hotel> byNumberOfRatings() {
        return gteNumberOfRatings == null ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Hotel.Fields.numberOfRatings.name()), gteNumberOfRatings);
    }
}
