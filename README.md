# DropDownBoxUi[![API](https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)[ ![Download](https://api.bintray.com/packages/jjhesk/maven/dropdownuilib/images/download.svg) ](https://bintray.com/jjhesk/maven/dropdownuilib/_latestVersion) [![gitpay](http://fc07.deviantart.net/fs70/f/2012/336/f/9/little_pixel_heart_by_tiny_bear-d5mtwiu.gif)](https://gratipay.com/jjhesk/)
========

A custom made drop down box Ui for handy use.

Version
========
Please see the [release log](https://github.com/jjhesk/DropDownBoxUi/releases/) for the latest updated version. Gradle setup is also ready. X.X.X to be the latest version possible you can find from the release log.

Recent Updates
========
v0.8.0
There is an update issue on the module TintImageView. It is notified and removed accordingly. The new module will fit for running API 23.1.0 or above.



buildscript
```gradle

repositories {
	    maven { url 'http://dl.bintray.com/jjhesk/maven/' }
}

dependencies {
  compile 'com.hkm.ui:dropdownuilib:1.0.0'
}
```

Apps using DropTouch
=============


Sample Scripts
============
The solution is very easy. I have wrapped pixlui inside the package and it will shared the library across the whole system. If you have a simple linear layout and you will just need to append them on the fly like so:
```java

 	setContentView(R.layout.activity_main);
        LinearLayout g = (LinearLayout) findViewById(R.id.lvt);

	//you can repeat that process however times you want.
        final Droptouch d = new Droptouch(this);
        d.setLabel("One The Thing");
        g.addView(d);

```

Present Layouts
=============

<img src="https://raw.github.com/jjhesk/DropDownBoxUi/master/screen/device-2015-03-24-102436.png" alt="screenshot" width="300px" height="auto" />
