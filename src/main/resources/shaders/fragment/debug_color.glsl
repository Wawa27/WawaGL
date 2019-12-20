#version 450

in vec3 oColor;
out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(oColor, 1);
}
