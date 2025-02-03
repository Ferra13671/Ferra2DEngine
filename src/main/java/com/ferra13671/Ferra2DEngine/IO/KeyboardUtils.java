package com.ferra13671.Ferra2DEngine.IO;


import java.util.HashSet;
import java.util.Set;

public class KeyboardUtils {

    private static final Set<Integer> activeKeys = new HashSet<>();

    public static void addActiveKey(int key) {
        activeKeys.add(key);
    }

    public static void removeActiveKey(int key) {
        activeKeys.remove(key);
    }

    public static boolean isKeyDown(int key) {
        return activeKeys.contains(key);
    }

    public static Set<Integer> getActiveKeys() {
        return activeKeys;
    }

    public static String getKeyName(int key) {
        switch (key) {
            case 48:
                return "0";

            case 49:
                return "1";
            case 50:
                return "2";
            case 51:
                return "3";
            case 52:
                return "4";
            case 53:
                return "5";
            case 54:
                return "6";
            case 55:
                return "7";
            case 56:
                return "8";
            case 57:
                return "9";
            case 65:
                return "A";
            case 66:
                return "B";
            case 67:
                return "C";
            case 68:
                return "D";
            case 69:
                return "E";
            case 70:
                return "F";
            case 71:
                return "G";
            case 72 :
                return "H";
            case 73 :
                return "I";
            case 74 :
                return "J";
            case 75 :
                return "K";
            case 76 :
                return "L";
            case 77 :
                return "M";
            case 78 :
                return "N";
            case 79 :
                return "O";
            case 80 :
                return "P";
            case 81 :
                return "Q";
            case 82 :
                return "R";
            case 83 :
                return "S";
            case 84 :
                return "T";
            case 85 :
                return "U";
            case 86 :
                return "V";
            case 87 :
                return "W";
            case 88 :
                return "X";
            case 89 :
                return "Y";
            case 90 :
                return "Z";
            case 290 :
                return "F1";
            case 291 :
                return "F2";
            case 292 :
                return "F3";
            case 293 :
                return "F4";
            case 294 :
                return "F5";
            case 295 :
                return "F6";
            case 296 :
                return "F7";
            case 297 :
                return "F8";
            case 298 :
                return "F9";
            case 299 :
                return "F10";
            case 300 :
                return "F11";
            case 301 :
                return "F12";
            case 302 :
                return "F13";
            case 303 :
                return "F14";
            case 304 :
                return "F15";
            case 305 :
                return "F16";
            case 306 :
                return "F17";
            case 307 :
                return "F18";
            case 308 :
                return "F19";
            case 309 :return  "F20";
            case 310 :return  "F21";
            case 311 :return  "F22";
            case 312 :return  "F23";
            case 313 :return  "F24";
            case 314 :return  "F25";
            case 282 :return  "NUMLOCK";
            case 320 :return  "NUMPAD0";
            case 321 :return  "NUMPAD1";
            case 322 :return  "NUMPAD2";
            case 323 :return  "NUMPAD3";
            case 324 :return  "NUMPAD4";
            case 325 :return  "NUMPAD5";
            case 326 :return  "NUMPAD6";
            case 327 :return  "NUMPAD7";
            case 328 :return  "NUMPAD8";
            case 329 :return  "NUMPAD9";
            case 330 :return  "NUMPADCOMMA";
            case 335 :return  "NUMPADENTER";
            case 336 :return  "NUMPADEQUALS";
            case 264 :return  "DOWN";
            case 263 :return  "LEFT";
            case 262 :return  "RIGHT";
            case 265 :return  "UP";
            case 334 :return  "ADD";
            case 39 :return  "APOSTROPHE";
            case 92 :return  "BACKSLASH";
            case 44 :return  "COMMA";
            case 61 :return  "EQUALS";
            case 96 :return  "GRAVE";
            case 91 :return  "LBRACKET";
            case 45 :return  "MINUS";
            case 332 :return  "MULTIPLY";
            case 46 :return  "PERIOD";
            case 93 :return  "RBRACKET";
            case 59 :return  "SEMICOLON";
            case 47 :return  "SLASH";
            case 32 :return  "SPACE";
            case 258 :return  "TAB";
            case 342 :return  "LALT";
            case 341 :return  "LCONTROL";
            case 340 :return  "LSHIFT";
            case 343 :return  "LWIN";
            case 346 :return  "RALT";
            case 345 :return  "RCONTROL";
            case 344 :return  "RSHIFT";
            case 347 :return  "RWIN";
            case 257 :return  "RETURN";
            case 256 :return  "ESCAPE";
            case 259 :return  "BACKSPACE";
            case 261 :return  "DELETE";
            case 269 :return  "END";
            case 268 :return  "HOME";
            case 260 :return  "INSERT";
            case 267 :return  "PAGEDOWN";
            case 266 :return  "PAGEUP";
            case 280 :return  "CAPSLOCK";
            case 284 :return  "PAUSE";
            case 281 :return  "SCROLLLOCK";
            case 283 :return  "PRINTSCREEN";
            case 0 :return  "NONE";
            default :return  "UNKNOWN";
        }
    }

    public static final int KEY_0 = 48;
    public static final int KEY_1 = 49;
    public static final int KEY_2 = 50;
    public static final int KEY_3 = 51;
    public static final int KEY_4 = 52;
    public static final int KEY_5 = 53;
    public static final int KEY_6 = 54;
    public static final int KEY_7 = 55;
    public static final int KEY_8 = 56;
    public static final int KEY_9 = 57;
    public static final int KEY_A = 65;
    public static final int KEY_B = 66;
    public static final int KEY_C = 67;
    public static final int KEY_D = 68;
    public static final int KEY_E = 69;
    public static final int KEY_F = 70;
    public static final int KEY_G = 71;
    public static final int KEY_H = 72;
    public static final int KEY_I = 73;
    public static final int KEY_J = 74;
    public static final int KEY_K = 75;
    public static final int KEY_L = 76;
    public static final int KEY_M = 77;
    public static final int KEY_N = 78;
    public static final int KEY_O = 79;
    public static final int KEY_P = 80;
    public static final int KEY_Q = 81;
    public static final int KEY_R = 82;
    public static final int KEY_S = 83;
    public static final int KEY_T = 84;
    public static final int KEY_U = 85;
    public static final int KEY_V = 86;
    public static final int KEY_W = 87;
    public static final int KEY_X = 88;
    public static final int KEY_Y = 89;
    public static final int KEY_Z = 90;
    public static final int KEY_F1 = 290;
    public static final int KEY_F2 = 291;
    public static final int KEY_F3 = 292;
    public static final int KEY_F4 = 293;
    public static final int KEY_F5 = 294;
    public static final int KEY_F6 = 295;
    public static final int KEY_F7 = 296;
    public static final int KEY_F8 = 297;
    public static final int KEY_F9 = 298;
    public static final int KEY_F10 = 299;
    public static final int KEY_F11 = 300;
    public static final int KEY_F12 = 301;
    public static final int KEY_F13 = 302;
    public static final int KEY_F14 = 303;
    public static final int KEY_F15 = 304;
    public static final int KEY_F16 = 305;
    public static final int KEY_F17 = 306;
    public static final int KEY_F18 = 307;
    public static final int KEY_F19 = 308;
    public static final int KEY_F20 = 309;
    public static final int KEY_F21 = 310;
    public static final int KEY_F22 = 311;
    public static final int KEY_F23 = 312;
    public static final int KEY_F24 = 313;
    public static final int KEY_F25 = 314;
    public static final int KEY_ENTER = 257;
    public static final int KEY_SHIFT = 340;
    public static final int KEY_NUMLOCK = 282;
    public static final int KEY_NUMPAD0 = 320;
    public static final int KEY_NUMPAD1 = 321;
    public static final int KEY_NUMPAD2 = 322;
    public static final int KEY_NUMPAD3 = 323;
    public static final int KEY_NUMPAD4 = 324;
    public static final int KEY_NUMPAD5 = 325;
    public static final int KEY_NUMPAD6 = 326;
    public static final int KEY_NUMPAD7 = 327;
    public static final int KEY_NUMPAD8 = 328;
    public static final int KEY_NUMPAD9 = 329;
    public static final int KEY_NUMPADCOMMA = 330;
    public static final int KEY_NUMPADENTER = 335;
    public static final int KEY_NUMPADEQUALS = 336;
    public static final int KEY_DOWN = 264;
    public static final int KEY_LEFT = 263;
    public static final int KEY_RIGHT = 262;
    public static final int KEY_UP = 265;
    public static final int KEY_ADD = 334;
    public static final int KEY_APOSTROPHE = 39;
    public static final int KEY_BACKSLASH = 92;
    public static final int KEY_COMMA = 44;
    public static final int KEY_EQUALS = 61;
    public static final int KEY_GRAVE = 96;
    public static final int KEY_LBRACKET = 91;
    public static final int KEY_MINUS = 45;
    public static final int KEY_MULTIPLY = 332;
    public static final int KEY_PERIOD = 46;
    public static final int KEY_RBRACKET = 93;
    public static final int KEY_SEMICOLON = 59;
    public static final int KEY_SLASH = 47;
    public static final int KEY_SPACE = 32;
    public static final int KEY_TAB = 258;
    public static final int KEY_LALT = 342;
    public static final int KEY_LCONTROL = 341;
    public static final int KEY_LSHIFT = 340;
    public static final int KEY_LWIN = 343;
    public static final int KEY_RALT = 346;
    public static final int KEY_RCONTROL = 345;
    public static final int KEY_RSHIFT = 344;
    public static final int KEY_RWIN = 347;
    public static final int KEY_RETURN = 257;
    public static final int KEY_ESCAPE = 256;
    public static final int KEY_BACKSPACE = 259;
    public static final int KEY_DELETE = 261;
    public static final int KEY_END = 269;
    public static final int KEY_HOME = 268;
    public static final int KEY_INSERT = 260;
    public static final int KEY_PAGEDOWN = 267;
    public static final int KEY_PAGEUP = 266;
    public static final int KEY_CAPSLOCK = 280;
    public static final int KEY_PAUSE = 284;
    public static final int KEY_SCROLLLOCK = 281;
    public static final int KEY_PRINTSCREEN = 283;
    public static final int PRESS = 1;
    public static final int RELEASE = 0;
    public static final int REPEAT = 2;
    public static final int MOUSE_BUTTON_LEFT = 0;
    public static final int MOUSE_BUTTON_MIDDLE = 2;
    public static final int MOUSE_BUTTON_RIGHT = 1;
    public static final int MOD_CONTROL = 2;
    public static final int CURSOR = 208897;
    public static final int CURSOR_DISABLED = 212995;
    public static final int CURSOR_NORMAL = 212993;
}
