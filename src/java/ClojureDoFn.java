package beam;

import org.apache.beam.sdk.transforms.DoFn;
import clojure.lang.IFn;

public class ClojureDoFn extends DoFn<Object, Object> {
    private IFn f;

    public ClojureDoFn(IFn f) {
        this.f = f;
    }

    @ProcessElement
    public void processElement(ProcessContext context) {
        f.invoke(context);
    }
}
