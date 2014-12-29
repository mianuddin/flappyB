package com.mianuddin.flappyB.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mianuddin.flappyB.flappyB;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(540, 960);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new flappyB();
        }
}