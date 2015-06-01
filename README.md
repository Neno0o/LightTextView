# LightTextView

Easy way to add textview above other views.

# Usage

LightTextView extends `RobotoTextView` which extends `TextView`.

``` java
LightTextView lightTextView = new LightTextView(this);
lightTextView.setText("CLICK");
lightTextView.setBackgroundColor(getResources().getColor(R.color.blue));
lightTextView.setCurrentView(button);
```
setPosition - LEFT_CORNER default

``` java
lightTextView.setPosition(LightTextView.Position.RIGHT_CORNER);
```
Customize textfont using RobotoTextView

``` java
Typeface typeface = RobotoTypefaceManager.obtainTypeface(
                getApplicationContext(),
                RobotoTypefaceManager.Typeface.ROBOTO_LIGHT_ITALIC);
RobotoTextViewUtils.setTypeface(lightTextView, typeface);
```
