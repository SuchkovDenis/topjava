package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        List<MealTo> withExcess = MealsUtil.getWithExcess(service.getAll(authUserId()), authUserCaloriesPerDay());
        log.info("getAll {}", withExcess);
        return withExcess;
    }

    public List<MealTo> getFiltered(LocalTime startTime, LocalTime endTime) {
        log.info("getAll with filter startTime = {}, endTime = {}", startTime, endTime);
        return MealsUtil.getFilteredWithExcess(service.getAll(authUserId()), authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get by id = {} by User with id = {}", id, authUserId());
        return service.get(authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create new Meal = {} by User with id = {}", meal, authUserId());
        meal.setUserId(authUserId());
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete by id = {} by User with id = {}", id, authUserId());
        service.delete(authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update Meal = {} by User with id = {}", meal, authUserId());
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }
}