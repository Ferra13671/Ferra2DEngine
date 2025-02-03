package com.ferra13671.Ferra2DEngine.Event.Events.Input;

public class CharInputEvent {
        private final char _char;

        public CharInputEvent(char _char) {
            this._char = _char;
        }

        public char getChar() {
            return _char;
        }
    }