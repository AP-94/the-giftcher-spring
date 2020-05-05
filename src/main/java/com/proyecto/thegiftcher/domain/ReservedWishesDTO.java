package com.proyecto.thegiftcher.domain;

import java.util.List;

public class ReservedWishesDTO {
    private Long friendId;
    private String friendName;
    private List<WishDTO> friendReservedWishes;

    public ReservedWishesDTO() {
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public List<WishDTO> getFriendReservedWishes() {
        return friendReservedWishes;
    }

    public void setFriendReservedWishes(List<WishDTO> friendReservedWishes) {
        this.friendReservedWishes = friendReservedWishes;
    }
}
