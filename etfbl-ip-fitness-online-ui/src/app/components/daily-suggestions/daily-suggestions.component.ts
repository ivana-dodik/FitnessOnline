import { CommonModule } from '@angular/common';
import { Component, NgModule, OnChanges, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ExerciseService } from 'src/app/services/exercise.service';

@Component({
  selector: 'app-daily-suggestions',
  standalone: true, // No need for standalone property
  imports: [CommonModule, FormsModule],
  templateUrl: './daily-suggestions.component.html',
  styleUrls: ['./daily-suggestions.component.css'] // Change styleUrl to styleUrls
})
export class DailySuggestionsComponent {
  muscleName: string = '';
  exercises: any[] = [];

  constructor(private exerciseService: ExerciseService) { }

  search() {
    this.searchExercises();
  }

  searchExercises(): void {
    if (this.muscleName.trim() !== '') {
      this.exerciseService.getExercises(this.muscleName)
        .subscribe(
          (exercises: any[]) => {
            this.exercises = exercises.map(exercise => ({
              name: exercise.name,
              instructions: exercise.instructions,
              level: exercise.difficulty
            }));
          },
          (error) => {
            console.error('Error fetching exercises:', error);
          }
        );
    }
  }
}
