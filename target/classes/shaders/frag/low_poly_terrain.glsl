#version 450

in vec3 oColor;

void main() {
    gl_FragColor = vec4(oColor, 1.0);
}
