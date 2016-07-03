#Circle Loading Animation

A cool circle loading animation inspired by [this post](http://www.materialup.com/posts/material-design-circle-loading) on [MaterialUp](http://www.materialup.com/)

![ezgif com-video-to-gif 3](https://cloud.githubusercontent.com/assets/15737675/16541358/dc99a04a-4080-11e6-84a2-d506a2656e35.gif) ![ezgif com-video-to-gif 2](https://cloud.githubusercontent.com/assets/15737675/16541338/434dd686-4080-11e6-8c12-0d69bfb65888.gif) ![ezgif com-video-to-gif 4](https://cloud.githubusercontent.com/assets/15737675/16541470/3329629e-4084-11e6-90d2-735529606594.gif) ![ezgif com-video-to-gif 5](https://cloud.githubusercontent.com/assets/15737675/16541635/f74cc2f8-4087-11e6-9bdc-b6c245b3a0be.gif)

##How to
####Gradle
```Gradle
dependencies {
    compile 'com.tbuonomo.andrui:circleloading:1.0.0'
}
```
####In your XML layout
```Xml
<com.tbuonomo.circleloading.CircleLoading
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:layout_centerInParent="true"
        app:innerColor="#2594fa"
        app:innerAlpha="255"
        app:outerColor="#1964a9"
        app:outerAlpha="180"
        app:animationDuration="1500"/>
```

####Attributes
| Attribute | Description |
| --- | --- |
| innerColor | Color of the inner circle |
| outerColor | Color of the outer circle |
| innerAlpha | Opacity of the inner circle beetween 0 and 255 (by default 255) |
| outerAlpha | Opacity of the outer circle beetween 0 and 255 (by default 120) |
| animationDuration | Step duration of the animation in ms (by default 500) |

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
