#version 460

in vec3 aTexCoord;

out vec4 gl_FragColor;

uniform samplerCube texture0;

void main() {
    gl_FragColor = texture(texture0, aTexCoord);
}
