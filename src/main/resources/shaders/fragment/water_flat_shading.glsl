#version 450

const int MAX_LIGHTS_COUNT = 64;

in flat vec4 oColor;
in vec3 oNormal;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(oColor.x, oColor.y, oColor.z, .4);
}
