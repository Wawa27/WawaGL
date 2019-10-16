#version 450

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

uniform sampler2DArray tex;
uniform int layer;

out vec4 gl_FragColor;

void main() {
    gl_FragColor = texture(tex, vec3(aTexCoord.x, aTexCoord.y, layer));
}
