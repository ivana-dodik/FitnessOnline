import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartModule } from 'angular-highcharts';
import Highcharts from 'highcharts';
import { ActivityLogsService } from 'src/app/services/activityLogs.service';
import { HighchartsChartModule } from 'highcharts-angular';


@Component({
  selector: 'app-weight-graph',
  standalone: true,
  imports: [ReactiveFormsModule, ChartModule, FormsModule],
  templateUrl: './weight-graph.component.html',
  styleUrl: './weight-graph.component.css'
})


export class WeightGraphComponent implements OnInit {
[x: string]: any;
  chartOptions: Highcharts.Options = {
    title: { text: 'Weightloss' },
    xAxis: { type: 'datetime' },
    series: []
  };
  startDate: string = '';
  endDate: string = '';

  constructor(private http: HttpClient, private activityLogsService: ActivityLogsService) { }

  ngOnInit(): void {}

  private formatDate(date: string): string {
    if (!date) {
      // Handle case where date is undefined or null
      return ''; // or handle it according to your use case
  }
    const [month, day, year] = date.split('/');
    // Assuming default time is 00:00:00
    return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')} 00:00:00`;
  }
  

  generateGraph(): void {
    //console.log(this.startDate + "      " + this.endDate);
    this.activityLogsService.getWeightOverTime(this.startDate, this.endDate).subscribe(data => {
      // Log every key-value pair
      Object.entries(data).forEach(([key, value]) => {
        console.log('Key: ${key}, Value: ${value}');
      });

      const seriesData = Object.entries(data).map(([key, value]) => [new Date(key).getTime(), value]);
      
        
      this.chartOptions.series = [{
        type: 'line', 
        name: 'date',
        data: seriesData
      }];
      Highcharts.chart('container', this.chartOptions);
    });
  }
}