package com.proyecto.thegiftcher.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reserved_wishes")
public class ReservedWishes implements Serializable {

    private static final long serialVersionUID = -3501942479934890361L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long wishId;

    @NotNull
    private Long friendId;

    public ReservedWishes() {
    }

    public ReservedWishes(Long id, @NotNull Long userId, @NotNull Long wishId, @NotNull Long friendId) {
        this.id = id;
        this.userId = userId;
        this.wishId = wishId;
        this.friendId = friendId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWishId() {
        return wishId;
    }

    public void setWishId(Long wishId) {
        this.wishId = wishId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservedWishes that = (ReservedWishes) o;
        return id.equals(that.id) &&
                userId.equals(that.userId) &&
                wishId.equals(that.wishId) &&
                friendId.equals(that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, wishId, friendId);
    }

    @Override
    public String toString() {
        return "ReservedWishes{" +
                "id=" + id +
                ", userId=" + userId +
                ", wishId=" + wishId +
                ", friendId=" + friendId +
                '}';
    }
}
