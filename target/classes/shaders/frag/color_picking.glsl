#version 450

uniform vec2 color;
layout(location = 0) out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(color, 0, 1);
}