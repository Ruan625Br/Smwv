## Quick start client


Open the file `app\src\main\java\com\nvidia\devtech\NvEventQueueActivity.java`
Add this code:
````java
 private void initMwv(FrameLayout frameLayout){
        mwvManager = new MwvManager(frameLayout, getInstance());
        mwvManager.setup();
}
````

!!! info
    Don't forget to initialize `mwvManager`

In the `systemInit` function:
````java
 //...
 protected boolean systemInit() {
     //...
     mRootFrame = findViewById(R.id.main_frame);
     
     //...
     
     initMwv(mRootFrame);
 }
````