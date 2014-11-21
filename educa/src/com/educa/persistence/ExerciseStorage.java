
package com.educa.persistence;

import com.educa.entity.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseStorage {

    private static List<Exercise> listExercise = new ArrayList<Exercise>();

    public ExerciseStorage() {
    }
    //
    //    public static void addExercise(Exercise exercise) {
    //        listExercise.add(exercise);
    //    }
    //
    //    public static void removeExercise(Exercise exercise) {
    //        listExercise.remove(exercise);
    //    }
    //
    //    public static void removeExercise(String name) {
    //        for (Exercise exerciseStorage : listExercise) {
    //
    //            if (exerciseStorage.getName().equals(name)) {
    //                removeExercise(exerciseStorage);
    //            }
    //        }
    //    }
    //
    //    public static List<Exercise> getListExercise() {
    //        return listExercise;
    //    }

}
