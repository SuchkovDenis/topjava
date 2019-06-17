package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(int userId, Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        if (!repository.delete(userId, id)) {
            throw new NotFoundException("There is no such meal or you are trying to delete meal, that is not yours!");
        }
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        Meal meal = repository.get(userId, id);
        if (meal == null) {
            throw new NotFoundException("There is no such meal or you are trying to get meal, that is not yours!");
        } else {
            return meal;
        }
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException{
        if (meal.getUserId() != null && userId == meal.getUserId()) {
            repository.save(meal);
        } else {
            throw new NotFoundException("There is no such meal or you are trying to get meal, that is not yours!");
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}