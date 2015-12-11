import {App, Platform} from 'ionic/ionic';
import '/js/app.scss';


@App({
  templateUrl: '/mine.html'
})
export class MyApp {
  constructor(platform: Platform) {

    platform.ready().then(() => {
      // Do any necessary cordova or native calls here now that the platform is ready
    });
  }
}
