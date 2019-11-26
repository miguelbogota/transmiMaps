package views.tools;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

import java.awt.Component;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A timing target that doesn't rely on public getters and setters.
 *
 * @version 1.0
 * @since 25/11/2019
 * @author Miguel Bogota
 */
public class PublicSetter<T> extends TimingTargetAdapter {
  private final AtomicReference<KeyFrames<T>> keyFrames;
  private final boolean isToAnimation;
  private final Getter<T> getter;
  private final Setter<T> setter;

  protected PublicSetter(KeyFrames<T> keyFrames, boolean isToAnimation, Getter<T> getter, Setter<T> setter) {
    this.keyFrames = new AtomicReference<KeyFrames<T>>(keyFrames);
    this.isToAnimation = isToAnimation;
    this.getter = getter;
    this.setter = setter;
  }

  public static <T> TimingTarget getTarget(Setter<T> setter, T... values) {
    return new PublicSetter<T>(new KeyFrames.Builder<T>().addFrames(values).build(), false, null, setter);
  }

  public static <T> TimingTarget getTarget(Setter<T> setter, KeyFrames<T> keyFrames) {
    return new PublicSetter<T>(keyFrames, false, null, setter);
  }

  public static <T> TimingTarget getTargetTo(Getter<T> getter, Setter<T> setter, T... values) {
    return getTargetTo(getter, setter, new KeyFrames.Builder<T>(values[0]).addFrames(values).build());
  }

  public static <T> TimingTarget getTargetTo(GetterAndSetter<T> getterAndSetter, T... values) {
    return getTargetTo(getterAndSetter, getterAndSetter, values);
  }

  public static <T> TimingTarget getTargetTo(Getter<T> getter, Setter<T> setter, KeyFrames<T> keyFrames) {
    return new PublicSetter<T>(keyFrames, true, getter, setter);
  }

  public static <T> TimingTarget getTargetTo(GetterAndSetter<T> getterAndSetter, KeyFrames<T> keyFrames) {
    return getTargetTo(getterAndSetter, getterAndSetter, keyFrames);
  }

  public static <U> Prop<U> animatableProperty(Component component, U value) {
    return new Prop<U>(component, value);
  }

  public void timingEvent(Animator source, double fraction) {
    setter.setValue(this.keyFrames.get().getInterpolatedValueAt(fraction));
  }

  public void begin(Animator source) {
    if (isToAnimation) {
      KeyFrames.Builder<T> builder = new KeyFrames.Builder<T>(getter.getValue());
      boolean first = true;
      for (KeyFrames.Frame<T> frame : keyFrames.get()) {
        if (first) {
          first = false;
        } else {
          builder.addFrame(frame);
        }
      }
      keyFrames.set(builder.build());
    }

    double fraction = source.getCurrentDirection() == Animator.Direction.FORWARD ? 0.0D : 1.0D;
    this.timingEvent(source, fraction);
  }

  public interface Getter<T> {
    T getValue();
  }

  public interface Setter<T> {
    void setValue(T value);
  }

  public interface GetterAndSetter<T> extends Getter<T>, Setter<T> {
  }

  /**
   * Property.
   * @param <T>
   */
  public static class Prop<T> implements GetterAndSetter<T> {
    private final Component component;
    private T value;

    public Prop(Component component, T value) {
      this.component = component;
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    public void setValue(T newValue) {
      value = newValue;
      if (component != null) {
        component.repaint();
      }
    }
  }
}