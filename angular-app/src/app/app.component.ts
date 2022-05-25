import { DOCUMENT } from '@angular/common';
import { Component, Inject } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'angular-app';

  constructor(@Inject(DOCUMENT) private document: Document) {}

  loadStyle() {
    let styleName = 'styles.css';
    // get head
    const head = this.document.getElementsByTagName('head')[0];

    // create link tag to load css
    let themeLink = this.document.getElementById('styles') as HTMLLinkElement;
    if (themeLink) {
      // if the link is already exist, we just replace the link source
      themeLink.href = styleName;
    } else {
      // if link doesn't exist, we create link tag
      const style = this.document.createElement('link');
      style.id = 'styles';
      style.rel = 'stylesheet';
      style.href = `${styleName}`;

      head.appendChild(style);
    }
  }

  ngOnInit() {
    this.loadStyle()
  }
}
