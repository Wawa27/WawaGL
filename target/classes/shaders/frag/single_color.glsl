#version 450

in vec3 aPosition;
uniform vec3 uColor;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = vec4(uColor.x + aPosition.y/10, uColor.y + aPosition.y/10, uColor.z + aPosition.y/10, 1);
}
