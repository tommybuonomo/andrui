#Fast Circle Loading Animation

A cool bounce circle loading animation inspired by [this post](http://www.materialup.com/posts/loading-interface) on [MaterialUp](http://www.materialup.com/)

![ezgif com-video-to-gif 9](https://cloud.githubusercontent.com/assets/15737675/16545557/a1a44c5c-412e-11e6-8737-25023a32403c.gif) ![ezgif com-video-to-gif 10](https://cloud.githubusercontent.com/assets/15737675/16545576/8409b1f4-412f-11e6-920c-8ff14ea1aa60.gif)
![ezgif com-video-to-gif 11](https://cloud.githubusercontent.com/assets/15737675/16545595/0161c83a-4130-11e6-8383-303ea6a4b4d5.gif) ![ezgif com-video-to-gif 12](https://cloud.githubusercontent.com/assets/15737675/16545735/6e0cdcc8-4134-11e6-91bb-8751d11a4314.gif)
##How to
###Installation
```Gradle
compile 'com.tbuonomo.andrui:bounceloading:0.1'
```
###Attributes
```Xml
<com.tbuonomo.bounceloading.BounceCircleLoading
    android:layout_width="70dp"
    android:layout_height="70dp"
    android:layout_centerInParent="true"
    app:backgroundColor="#1DE9B6"
    app:pointColor="#383838"
    app:pointSize="8dp"
    app:interpolator="anticipate_overshoot"
    app:animationDuration="800"/>
```

| Attribute | Description |
| --- | --- |
| `pointColor` | Color of the points |
| `backgroundColor` | Color of the background circle |
| `pointSize` | Size of the points in dp (by default 5dp) |
| `interpolator` | The interpolator to use for the points animation (by default `fastOutSlowIn`) |
| `animationDuration` | Step duration of the animation in ms (by default 700) |

##License
    Copyright 2016 Tommy Buonomo
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
