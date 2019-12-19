#version 450

const int MAX_LIGHTS_COUNT = 64;

in flat vec4 oColor;
in vec4 oPosition;

out vec4 gl_FragColor;

void main() {
    float white = min(.2, oPosition.y / 4 + .3);
    gl_FragColor = vec4(oColor.x + white, oColor.y + white, oColor.z + white * 1.5, .8f);
}
