package it.tiwiz.whatsong.views;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import it.tiwiz.whatsong.R;

/**
 * This custom {@link android.view.View} encapsulates the {@link android.view.animation.Animation}s
 * needed by the app when changing the icon of the provider. In this way, whenever the icon is changed,
 * a flip-out-flip-in animation shows the change.
 */
public class AnimatedImageView extends ImageView{

    private static final int NOT_SET = -1;

    private int currentIconResource;
    private Animation animationOut, animationIn;
    private Animation.AnimationListener animationOutListener, animationInListener;

    public AnimatedImageView(Context context) {
        super(context);
        initImageView();
    }

    public AnimatedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageView();
    }

    public AnimatedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initImageView();
    }

    /**
     * This method initializes the {@link android.view.animation.Animation}s and the
     * {@link android.view.animation.Animation.AnimationListener}s in order keep a reference to
     * them when the {@link android.widget.ImageView} is alive
     */
    private void initImageView() {
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.flip_side_out);
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.flip_side_in);

        animationOutListener = new ImageOutAnimationListener();
        animationInListener = new ImageInAnimationListener();

        animationOut.setAnimationListener(animationOutListener);
        animationIn.setAnimationListener(animationInListener);

        currentIconResource = NOT_SET;
    }

    /**
     * Instead of directly calling the {@code super} method, we use rules to set the animation when
     * needed, invoking the {@code super} method directly if it's the first time the image is set
     */
    @Override
    public void setImageResource(int resId) {
        if (hasImageBeenChanged(resId)) {
            updateImageResource(resId);
        }
    }

    private boolean hasImageBeenChanged(int newResourceId) {
        return !(newResourceId == currentIconResource);
    }

    /**
     * If the {@link android.widget.ImageView} has no image set, we update it directly, otherwise we
     * animate the change with proper methods.
     *
     * @see #updateImageResourceWithoutAnimation() Updating the image without animation
     * @see #updateImageResourceWithAnimation() Updating the image with animation
     */
    private void updateImageResource(int newResourceId) {
        currentIconResource = newResourceId;
        if (currentIconResource == NOT_SET) {
            updateImageResourceWithoutAnimation();
        } else {
            updateImageResourceWithAnimation();
        }
    }

    /**
     * This method is a mere wrapper of the {@code super} method for a cleaner code solution.
     * <b>Note</b>: this method gets called every time after the animation has been performed
     */
    private void updateImageResourceWithoutAnimation() {
        super.setImageResource(currentIconResource);
    }

    /**
     * This method animates the change of images and gets called evey time the image is changed.
     */
    private void updateImageResourceWithAnimation() {
        startAnimation(animationOut);
    }

    /**
     * This class represents the first part of the {@link android.view.animation.Animation}, taking care of
     * the old image flipping out of the {@link android.view.View}.
     *
     * @see #setImageResource(int) Set the new image resource ID
     */
    private class ImageOutAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            startAnimation(animationIn);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

    /**
     * This class represents the second part of the {@link android.view.animation.Animation}, taking care of
     * the new image flipping in inside the {@link android.view.View}
     */
    private class ImageInAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            updateImageResourceWithoutAnimation();
        }

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

}
