#version 450

in vec2 aTexCoord;
in vec3 aPosition;
in vec3 aNormal;

out vec4 gl_FragColor;

uniform sampler2D texture0;

void main() {
    gl_FragColor = vec4(aPosition, 1);
}
