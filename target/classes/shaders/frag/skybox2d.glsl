#version 460

in vec2 aTexCoord;

out vec4 gl_FragColor;

uniform sampler2D texture0;

void main() {
    gl_FragColor = texture(texture0, aTexCoord);
}
