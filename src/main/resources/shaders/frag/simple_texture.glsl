#version 450

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

uniform sampler2D tex;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = texture(tex, aTexCoord);
}
