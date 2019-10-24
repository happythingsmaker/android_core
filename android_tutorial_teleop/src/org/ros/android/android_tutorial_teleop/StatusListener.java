/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.android.android_tutorial_teleop;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

//import android.util.Log;


/**
 * A simple {@link Subscriber} {@link NodeMain}.
 *
 * @author damonkohler@google.com (Damon Kohler)
 */
public class StatusListener extends AbstractNodeMain {
    private String topicName;
    TopicCallback topicCallback;

    interface TopicCallback {
        public void callbackCall(String string);
    }
    public StatusListener(TopicCallback topicCallback, String topicName ){
        this.topicName = topicName;
        this.topicCallback = topicCallback;

    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava_tutorial_pubsub/StatusListener");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Log log = connectedNode.getLog();
        Subscriber<geometry_msgs.Twist> subscriber = connectedNode.newSubscriber(topicName, geometry_msgs.Twist._TYPE);
        subscriber.addMessageListener(new MessageListener<geometry_msgs.Twist>() {
            @Override
            public void onNewMessage(geometry_msgs.Twist message) {
                log.info("I heard: \"" + message.getLinear().getY() + "\"");
                topicCallback.callbackCall(message.getLinear().getX() + "");

            }
        });
    }
}
