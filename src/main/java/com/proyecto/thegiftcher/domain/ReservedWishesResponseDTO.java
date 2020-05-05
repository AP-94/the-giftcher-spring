package com.proyecto.thegiftcher.domain;

import java.util.List;

public class ReservedWishesResponseDTO {
    private List<ReservedWishesDTO> reservedWishes;

    public ReservedWishesResponseDTO() {
    }

    public ReservedWishesResponseDTO(List<ReservedWishesDTO> reservedWishes) {
        this.reservedWishes = reservedWishes;
    }

    public List<ReservedWishesDTO> getReservedWishes() {
        return reservedWishes;
    }

    public void setReservedWishes(List<ReservedWishesDTO> reservedWishes) {
        this.reservedWishes = reservedWishes;
    }
}
