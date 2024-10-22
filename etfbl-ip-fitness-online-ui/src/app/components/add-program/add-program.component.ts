import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AttributeEntity, AttributeIdValue } from 'src/app/interfaces/attribute';
import { Category } from 'src/app/interfaces/category';
import { AddProgramDto } from 'src/app/interfaces/item';
import { CategoryService } from 'src/app/services/category.service';
import { ProgramService } from 'src/app/services/program.service';
import StartToastifyInstance from 'toastify-js';

@Component({
  selector: 'app-add-program',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './add-program.component.html',
  styleUrl: './add-program.component.css'
})
export class AddProgramComponent implements OnInit {
  public firstPartForm: FormGroup = new FormGroup({});
  public secondPartForm: FormGroup = new FormGroup({});
  public thirdPartForm: FormGroup = new FormGroup({});

  public srcIndex: number = 0;
  public currentStep: number = 1;

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private programService: ProgramService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  addProgramDto!: AddProgramDto;

  selectedDiffLvl : string = '';

  images: File[] = [];
  imageURLs: string[] = [];

  categories: Category[] = [];
  categoriesToShow: Category[] = [];
  selectedCategoryName: string = '';
  attributes: AttributeEntity[] = [];

  enteredAttributes: AttributeIdValue[] = [];

  ngOnInit(): void {

    this.firstPartForm = this.formBuilder.group({
      title: ['', Validators.required],
    });

    this.secondPartForm = this.formBuilder.group({
      price: ['', Validators.required],
      location: ['', Validators.required],
      durationMinutes: [0, Validators.required],
      contactPhone: ['', Validators.required],
      description: ['', Validators.required],
      difficultyLevel: ['', Validators.required]
    });

    this.thirdPartForm = this.formBuilder.group({
      categoryId: ['', Validators.required],
    });
  }


  goBack() {
    if (this.currentStep > 1) this.currentStep -= 1;

    this.populateImages();
  }

  submitFirstPart() {
    if (!this.firstPartForm.valid) {
      StartToastifyInstance({
        text: 'Please enter title before proceeding',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
      return;
    }

    this.currentStep = 2;
  }

  submitSecondPart() {
    if (!this.secondPartForm.valid) {
      StartToastifyInstance({
        text: 'Please enter all the information before proceeding',
        offset: {
          x: 25,
          y: '85vh',
        },
      }).showToast();
      return;
    }

    this.currentStep = 3;

    this.categoryService.getAll().subscribe({
      next: (res) => {
        this.categories = res;
        this.resetCategorySelection();
      },
      error: () =>
        StartToastifyInstance({
          text: 'Error while fetching categories',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast(),
    });
  }

  resetCategorySelection() {
    this.selectedCategoryName = '';
    this.categoriesToShow = this.categories.filter((c) => !c.parentCategoryId);
    this.attributes = [];
    this.thirdPartForm.reset();

    this.thirdPartForm.value.categoryId = null;
  }

  submitAll() {
    this.addProgramDto = this.secondPartForm.value;
    this.addProgramDto.difficultyLevel = this.secondPartForm.value.difficultyLevel;
    this.addProgramDto.title = this.firstPartForm.value.title;
    this.addProgramDto.categoryId = this.thirdPartForm.value.categoryId;
    this.addProgramDto.attributes = this.enteredAttributes.filter(
      (a) => a.value && a.value != ''
    );

    this.programService.addNewProgram(this.addProgramDto).subscribe({
      next: (res) => {
        StartToastifyInstance({
          text: 'Successfully added program.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
        if (this.images.length > 0) {
          this.programService.uploadImagesForProgram(res.id, this.images)
            .subscribe({
              next: () => {
                StartToastifyInstance({
                  text: 'Successfully added program image.',
                  offset: {
                    x: 25,
                    y: '85vh',
                  },
                }).showToast();
                this.router.navigate(['/programs']);
              },
              error: () => {
                StartToastifyInstance({
                  text: 'Error while adding program image.',
                  offset: {
                    x: 25,
                    y: '85vh',
                  },
                }).showToast();
              },
            }
          );
        }
        else {
          this.router.navigate(['/programs']);
        }

      },
      error: () => {
        StartToastifyInstance({
          text: 'Error while adding program.',
          offset: {
            x: 25,
            y: '85vh',
          },
        }).showToast();
      },
    });
  }

  populateImages() {
    this.imageURLs = [];
    for (let i = 0; i < this.images.length; i++) {
      this.imageURLs.push(URL.createObjectURL(this.images[i]));
    }
  }

  public onFileSelected(event: any) {
    if (event.target.files.length == 0) {
      return;
    }

    this.images = <File[]>event.target.files;
    this.populateImages();
  }

  handleNext() {
    this.srcIndex = (this.srcIndex + 1) % this.images.length;
  }

  handlePrevious() {
    if (this.srcIndex == 0) {
      this.srcIndex = this.images.length - 1;
    } else {
      this.srcIndex = (this.srcIndex - 1) % this.images.length;
    }
  }

  handleCategorySelect(event: any) {
    let selectedId = event.target.value;
    if (!selectedId) {
      return;
    }

    console.log('SELECTED: ' + selectedId);

    this.selectedCategoryName +=
      ' > ' + this.categories.find((c) => c.id == selectedId)?.name;
    this.categoriesToShow = [];
    this.categoriesToShow = this.categories.filter(
      (c) => c.parentCategoryId == selectedId || c.id == selectedId
    );

    this.enteredAttributes = [];
    this.categoryService
      .getAttributesByCategoryId(event.target.value)
      .subscribe({
        next: (res) => (this.attributes = res),
        error: () =>
          StartToastifyInstance({
            text: 'Error while retrieving attributes',
            offset: {
              x: 25,
              y: '85vh',
            },
          }).showToast(),
      });
  }

  handleAttributeInput(input: string, attributeId: number) {
    let found = false;
    let newAttribute = { attributeId: attributeId, value: input };

    for (let i = 0; i < this.enteredAttributes.length; i++) {
      if (this.enteredAttributes[i].attributeId == attributeId) {
        found = true;
        this.enteredAttributes[i].value = input;
      }
    }

    if (!found) {
      this.enteredAttributes.push(newAttribute);
    }

    return;
  }
}