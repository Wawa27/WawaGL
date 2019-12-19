#version 450

const int MAX_LIGHTS_COUNT = 64;

in flat vec4 oColor;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(oColor.xyz, 1);
}
