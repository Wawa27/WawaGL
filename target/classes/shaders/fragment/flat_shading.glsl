#version 450

const int MAX_LIGHTS_COUNT = 64;

in flat vec4 oColor;
in flat vec3 oPosition;

out vec4 gl_FragColor;

void main() {
    if (oPosition.y <= .3) discard;
    gl_FragColor = vec4(oColor.xyz, 1);
}
