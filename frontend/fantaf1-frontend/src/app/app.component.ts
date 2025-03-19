import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Fantaf1BffService } from '../service/fantaf1-bff.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  constructor(private readonly _bffService: Fantaf1BffService) {}

  ngOnInit(): void {
    this._bffService.getDriversByYear(2021).subscribe((res) => {
      console.log(res);
    });
  }
}
