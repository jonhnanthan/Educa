
package com.educa.persistence;

import com.educa.entity.Exercise;

import java.util.List;

public interface StorageSystem {

    public void addExercise(Exercise exercise);

    public void deleteExercise(Integer id);

	public void deleteExercise(Exercise exercise);
	
    public void editExercise(Exercise exercise);

    public Exercise getExercise(int key);

    public List<Exercise> getExercises();


}
