package org.ros.android.android_tutorial_teleop;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

//package org.ros.rosjava_tutorial_pubsub;


/**
 * A simple {@link Publisher} {@link NodeMain}.
 *
 * @author damonkohler@google.com (Damon Kohler)
 */
public class SafeBumperSwitcher extends AbstractNodeMain {
    private String topic_name;

    public SafeBumperSwitcher() {
        topic_name = "safe";
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android_app/bumper");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        final Publisher<std_msgs.Bool> publisher =
                connectedNode.newPublisher(topic_name, std_msgs.Bool._TYPE);
        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                std_msgs.Bool state = publisher.newMessage();
                state.setData(true);
                publisher.publish(state);
                Thread.sleep(50);
            }
        });
    }
}
