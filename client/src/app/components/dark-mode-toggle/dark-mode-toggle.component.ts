import { OverlayContainer } from '@angular/cdk/overlay';
import { Component, HostBinding, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-dark-mode-toggle',
  templateUrl: './dark-mode-toggle.component.html',
  styleUrls: ['./dark-mode-toggle.component.scss'],
})
export class DarkModeToggleComponent implements OnInit {
  @Output() onDarkModeEnable = new BehaviorSubject(false);

  darkModeEnabled: boolean = false;
  darkToggleControl = new FormControl('');

  ngOnInit(): void {
    this.setDarkMode();
  }

  setDarkMode() {
    // Initially check if dark mode is enabled on system
    const darkModeOn =
      window.matchMedia &&
      window.matchMedia('(prefers-color-scheme: dark)').matches;
    console.log('Device darkModeOn:', darkModeOn);
    this.darkModeEnabled = darkModeOn;

    this.darkToggleControl.valueChanges.subscribe((darkMode) => {
      console.log('Toggle darkMode: ', darkMode);
      this.darkModeEnabled = darkMode == 'true';
      this.onDarkModeEnable.next(this.darkModeEnabled);
    });
    // set the toggle initial state to the device dark mode preference
    this.darkToggleControl.setValue(darkModeOn.toString());
  }
}
