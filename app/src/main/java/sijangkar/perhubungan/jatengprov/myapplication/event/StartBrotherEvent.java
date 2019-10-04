package sijangkar.perhubungan.jatengprov.myapplication.event;

import sijangkar.perhubungan.jatengprov.myapplication.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class StartBrotherEvent {
    public SupportFragment targetFragment;

    public StartBrotherEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
