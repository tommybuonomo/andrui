#Fast Circle Loading Animation

A cool fast circle loading animation inspired by [this post](http://www.materialup.com/posts/loading-animation-interface) on [MaterialUp](http://www.materialup.com/)

![ezgif com-video-to-gif 6](https://cloud.githubusercontent.com/assets/15737675/16543142/41550baa-40c7-11e6-81a0-54319f2ab15e.gif) ![ezgif com-video-to-gif 7](https://cloud.githubusercontent.com/assets/15737675/16544800/2beff760-4113-11e6-9dc4-c9304e93c657.gif)
![ezgif com-video-to-gif 13](https://cloud.githubusercontent.com/assets/15737675/16546782/fe6714f2-4155-11e6-9d14-9a2770913ab7.gif) ![ezgif com-video-to-gif 14](https://cloud.githubusercontent.com/assets/15737675/16546787/3fb741ac-4156-11e6-8a98-4ece3e29f431.gif)


##How to
####Gradle
```Gradle
dependencies {
    compile 'com.tbuonomo.andrui:fastcircleloading:1.0.0'
}
```
####In your XML layout
```Xml
<com.tbuonomo.fastcircleloading.FastCircleLoading
    android:layout_width="30dp"
    android:layout_height="30dp"
    android:layout_alignParentRight="true"
    android:layout_alignParentTop="true"
    app:backgroundColor="#D50000"
    app:pointColor="#424242"
    app:pointSize="5dp"
    app:interpolator="anticipate_overshoot"
    app:animationDuration="5000"/>
```

####Attributes
| Attribute | Description |
| --- | --- |
| `pointColor` | Color of the points |
| `backgroundColor` | Color of the background circle |
| `pointSize` | Size of the points in dp (by default 5dp) |
| `interpolator` | The interpolator to use for the points animation (by default `fastOutSlowIn`) |
| `animationDuration` | Step duration of the animation in ms (by default 2000) |

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
