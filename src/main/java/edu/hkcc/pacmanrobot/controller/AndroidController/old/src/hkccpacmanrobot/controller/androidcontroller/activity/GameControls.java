package edu.hkcc.pacmanrobot.controller.AndroidController.old.src.hkccpacmanrobot.controller.androidcontroller.activity;

public class GameControls extends Activity implements OnTouchListener {
    public float init_x = 514;
    public float init_y = 934;
    public Point _touchingPoint = new Point(514, 934);
    public Point _pointerPosition = new Point(0, 0);
    private Boolean _dragging = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        update(event);
        return true;
    }

    private MotionEvent lastEvent;

    public void update(MotionEvent event) {
        if (event == null && lastEvent == null) {
            return;
        } else if (event == null && lastEvent != null) {
            event = lastEvent;
        } else {
            lastEvent = event;
        }
        //drag drop
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _dragging = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            _dragging = false;
        }

        if (_dragging) {
            // get the position
            _touchingPoint.x = (int) event.getX();
            _touchingPoint.y = (int) event.getY();

            // bound to a box
/*
            if (_touchingPoint.x < 400) {
                _touchingPoint.x = 400;
            }
            if (_touchingPoint.x > 450) {
                _touchingPoint.x = 450;
            }
            if (_touchingPoint.y < 240) {
                _touchingPoint.y = 240;
            }
            if (_touchingPoint.y > 290) {
                _touchingPoint.y = 290;
            }
*/
            //get the angle
            double angle = Math.atan2(_touchingPoint.y - init_y, _touchingPoint.x - init_x) / (Math.PI / 180);

            // Move the beetle in proportion to how far
            // the joystick is dragged from its center
            _pointerPosition.y += Math.sin(angle * (Math.PI / 180)) * (_touchingPoint.x / 70);
            _pointerPosition.x += Math.cos(angle * (Math.PI / 180)) * (_touchingPoint.x / 70);

/*            //make the pointer go thru
               if (_pointerPosition.x > 480) {
                _pointerPosition.x = 0;
            }

            if (_pointerPosition.x < 0) {
                _pointerPosition.x = 480;
            }

            if (_pointerPosition.y > 320) {
                _pointerPosition.y = 0;
            }
            if (_pointerPosition.y < 0) {
                _pointerPosition.y = 320;
            }
*/
        } else if (!_dragging) {
            // Snap back to center when the joystick is released
            _touchingPoint.x = (int) init_x;
            _touchingPoint.y = (int) init_y;
            // shaft.alpha = 0;
        }
    }
}
