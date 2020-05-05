package com.proyecto.thegiftcher.service;

import com.proyecto.thegiftcher.domain.ReservedWishes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IReservedWishesService {
    List<ReservedWishes> getAll(HttpServletRequest request);

    ReservedWishes create(ReservedWishes reservedWish, HttpServletRequest request);

    void delete(long id);
}
