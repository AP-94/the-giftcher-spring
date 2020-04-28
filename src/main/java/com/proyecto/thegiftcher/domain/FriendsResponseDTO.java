package com.proyecto.thegiftcher.domain;

import java.util.List;

public class FriendsResponseDTO {
    private List<FriendDTO> friends;

    public FriendsResponseDTO() {
    }

    public FriendsResponseDTO(List<FriendDTO> friends) {
        this.friends = friends;
    }

    public List<FriendDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendDTO> friends) {
        this.friends = friends;
    }
}
