package Drivers;

public enum Browser
{
    CHROME {
        @Override
        public AbstractDriver getDriverFactory() {
            return new Chromefactory();
        }
    },
    SAFARI {
        @Override
        public AbstractDriver getDriverFactory() {
            return new SafariFactory();
        }
    },
    FIREFOX {
        @Override
        public AbstractDriver getDriverFactory() {
            return new firefoxFactory();
        }
    },
    EDGE {
        @Override
        public AbstractDriver getDriverFactory() {
            return new EdgeFactory();
        }
    };

    public abstract AbstractDriver getDriverFactory();
}