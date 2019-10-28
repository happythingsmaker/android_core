package org.ros.android.android_tutorial_teleop;

import android.util.Log;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

import std_msgs.UInt8;

//package org.ros.rosjava_tutorial_pubsub;


/**
 * A simple {@link Publisher} {@link NodeMain}.
 *
 * @author damonkohler@google.com (Damon Kohler)
 */
public class Lifter extends AbstractNodeMain {
    private String topic_name;
    private boolean direction = true; // true : up, false: down

    public Lifter() {
        topic_name = "lifter";
    }
    public void setDirectrion(boolean b){
        this.direction = b;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("android_app/lifter");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            @Override
            protected void loop() throws InterruptedException {
                final Publisher<std_msgs.UInt8> up_pub =
                        connectedNode.newPublisher("/PWM2", UInt8._TYPE);
                std_msgs.UInt8 pwm_ch2= up_pub.newMessage();

                final Publisher<std_msgs.UInt8> down_pub =
                        connectedNode.newPublisher("/PWM3", UInt8._TYPE);
                std_msgs.UInt8 pwm_ch3 = down_pub.newMessage();

                if (direction){
                    pwm_ch2.setData((byte) 255);
                    pwm_ch3.setData((byte) 0);
                }else{
                    pwm_ch2.setData((byte) 0);
                    pwm_ch3.setData((byte) 255);
                }

                up_pub.publish(pwm_ch2);
                down_pub.publish(pwm_ch3);

                // This CancellableLoop will be canceled automatically when the node shuts
                // down.
                Log.d("lifter", "clicked");
                Thread.sleep(50);

            }
        });
    }
}
