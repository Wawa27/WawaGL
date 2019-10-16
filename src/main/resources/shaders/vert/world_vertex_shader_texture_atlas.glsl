#version 450

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec2 aTexCoord;
out vec3 aNormal;
out vec4 gl_Position;
out vec3 aPosition;

uniform int cellCount;
uniform vec2 offset;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    aPosition = vec3(view * model * vec4(position, 1.0));
    aNormal = normalize(transpose(inverse(mat3(view * model))) * normal);
    aTexCoord = vec2(texCoord.x / cellCount, texCoord.y / cellCount) + offset;

    gl_Position = projection * view * model * vec4(position, 1.0);
}
